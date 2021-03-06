(ns fierce-lambda.s3
  (:require [amazonica.aws.s3 :as s3]
            [cheshire.core :as json]
            [clojure.java.io :as io]
            [taoensso.timbre :as logging]))

(defn put-json!
  "PUT this data as json to to the path in the S3 bucket"
  [bucket path data]
  (logging/info "Putting data to " path)
  (let [json-bytes (.getBytes (json/generate-string data))]
    (with-open [is (io/input-stream json-bytes)]
      (s3/put-object :bucket-name bucket
                     :key path
                     :input-stream is
                     :metadata {:content-length (count json-bytes)}))))

(defn get-json
  "Given an s3 bucket and key, will return a map of the json"
  [bucket key]
  (-> (s3/get-object bucket key)
      :input-stream
      io/reader
      (json/parse-stream true)))
