(defproject org.harto/huon "0.5.1"
  :description "ClojureScript interface to goog.debug.Logger"
  :url "https://github.com/harto/huon"
  :license {:name "MIT"
            :url "https://github.com/harto/huon/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]]
  :profiles {:dev {:plugins [[lein-cljsbuild "1.1.7"
                               :exclusions [[org.clojure/clojure]]]]
                   :cljsbuild {:builds ~(into {}
                                              (for [target [:browser :nodejs]
                                                    optimizations [:none
                                                                   :whitespace
                                                                   :simple
                                                                   :advanced]
                                                    ;; whitespace optimizations not supported for nodejs
                                                    :when (not (and (= target :nodejs)
                                                                    (= optimizations :whitespace)))
                                                    :let [id (format "test-%s-%s" (name target) (name optimizations))]]
                                                [id {:source-paths ["src" "test"]
                                                     :compiler {:main "huon.tests"
                                                                :target (when-not (= target :browser) target)
                                                                :optimizations optimizations
                                                                :output-to (format "target/%s.js" id)
                                                                :output-dir (format "target/%s" id)
                                                                :asset-path id}}]))}}})
