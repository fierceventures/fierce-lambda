(defproject fierce/fierce-lambda "0.0.2"
  :author "Stuart King <https://fierce.ventures>"
  :description "Utils for AWS Lambda functions"
  :license {:name         "Eclipse Public License"
            :url          "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments     "Same as Clojure"}
  :url "https://github.com/fierceventures/fierce-lambda"
  :dependencies [[amazonica "0.3.111" :exclusions [com.amazonaws/aws-java-sdk
                                                   com.amazonaws/amazon-kinesis-client]]
                 [com.amazonaws/aws-java-sdk-core "1.11.176"]
                 [com.amazonaws/aws-java-sdk-lambda "1.11.179"]
                 [com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [fierce/timbre-lambda "0.0.1"]]
  :source-paths ["src/main/clj"]
  :test-paths ["src/test/clj"]
  :profiles {:uberjar {:aot :all}})
