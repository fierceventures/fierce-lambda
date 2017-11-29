(defproject fierce/fierce-lambda "0.0.8"
  :author "Stuart King <https://fierce.ventures>"
  :description "Utils for AWS Lambda functions"
  :license {:name         "Eclipse Public License"
            :url          "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments     "Same as Clojure"}
  :url "https://github.com/fierceventures/fierce-lambda"
  :dependencies [[fierce/timbre-lambda "0.0.2"]
                 [amazonica "0.3.111" :exclusions [com.amazonaws/aws-java-sdk
                                                   com.amazonaws/amazon-kinesis-client]]
                 [cheshire "5.8.0"]
                 [com.amazonaws/aws-java-sdk-core "1.11.176"]
                 [com.amazonaws/aws-java-sdk-lambda "1.11.179"]
                 [com.amazonaws/aws-java-sdk-s3 "1.11.176"]
                 [com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [org.clojure/clojure "1.8.0"]
                 [com.taoensso/timbre "4.10.0"]]
  :source-paths ["src/main/clj"]
  :test-paths ["src/test/clj"]
  :profiles {:uberjar {:aot :all}})
