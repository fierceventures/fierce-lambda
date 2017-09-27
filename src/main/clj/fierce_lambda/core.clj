(ns fierce-lambda.core
  (:require [amazonica.aws.lambda :as lambda]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.string :as s]
            [timbre-lambda.logging :as logging])
  (:import (java.io ByteArrayOutputStream)))

(defn read-is
  "Reads a json input stream into clojure"
  [input-stream]
  (with-open [reader (io/reader input-stream)]
    (json/read reader :key-fn keyword)))

(defn write-to-os
  "Write the event to the os if the event is non-nil"
  [event os]
  (when event
    (with-open [writer (io/writer os)]
      (json/write event writer :key-fn name)
      (.flush writer))))

(defn qualify
  "Qualify the class name with the package (namespace)"
  [class-name]
  (let [ns (s/replace (str *ns*) #"-" "_")]
    (str ns "." class-name)))

(defn camel-case
  "Return a camelcase class name of the function name"
  [fname]
  (let [pieces (s/split (str fname) #"-")
        remove-chars #(s/replace % #"[^a-zA-Z]" "")
        work (comp s/capitalize remove-chars)]
    (apply str (mapcat work pieces))))

(defn make-lambda
  "Create an implementation of RequestStreamHandler with logging configured."
  [class-name fn-name handler-fn]
  (eval `(defn ~'-handleRequest [this# in# os# ctx#]
           (logging/configure-logging ctx#)
           (-> in#
               read-is
               (#(~handler-fn % ctx#))
               (write-to-os os#))))

  (eval `(gen-class
           :name ~(-> class-name qualify symbol)
           :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler]))

  (eval `(defn ~(symbol fn-name)
           [~'data]
           (with-open [os# (ByteArrayOutputStream. 4096)
                       writer# (io/writer os#)]
             (json/write ~'data writer#)
             (-> (lambda/invoke :function-name ~fn-name
                                :payload (.toString os#))
                 (#(if-let [err# (:function-error %)]
                     (throw (ex-info (str "Lambda Error: " err#) %))
                     %))
                 :payload
                 .array
                 io/input-stream
                 io/reader
                 json/read)))))

(defmacro deflambda
  [fname args & body]
  (let [class-name (camel-case fname)]
    `(make-lambda ~class-name ~(str fname) (fn ~args ~@body))))
