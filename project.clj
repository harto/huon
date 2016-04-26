(defproject org.harto/huon "0.2.1"
  :description "ClojureScript interface to goog.debug.Logger"
  :url "https://github.com/harto/huon"
  :license {:name "MIT"
            :url "https://github.com/harto/huon/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]]
  :profiles {:dev {:plugins [[lein-cljsbuild "1.1.3"
                              :exclusions [[org.clojure/clojure]]]]
                   :cljsbuild {:builds [{:id "test"
                                         :source-paths ["src" "test"]
                                         :compiler {:main huon.tests
                                                    :target :nodejs
                                                    :optimizations :none
                                                    :output-to "target/test/tests.js"
                                                    :output-dir "target/test/out"}}]}}})
