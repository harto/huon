(ns huon.tests
  (:require [huon.log :as log]
            [huon.tests-2]))

(log/configure! {:show-level? false
                 :format str})

(defn ^:export main []
  ;; check messages not logged below threshold
  (doseq [level [:debug :info :warn :error]]
    (log/configure! {:root-level level})
    (log/debug "(log/debug …) logs" level "messages")
    (log/info "(log/info …) logs" level "messages")
    (log/warn "(log/warn …) logs" level "messages")
    (log/error "(log/error …) logs" level "messages")
    ;; specify log level at runtime
    (log/log level (str "(log/log " level " …)") "logs" level "messages"))

  ;; check messages not evaluated below threshold
  (log/set-root-level! :error)
  (log/info (throw (js/Error. "shouldn't be evaluated")))

  ;; check formatting of various objects
  (log/set-root-level! :debug)
  (log/debug {:foo "bar" :baz 42})
  (log/debug '(a b c d foo yadda-yadda))
  (log/debug #js{:foo 42})

  ;; show log level in message
  (log/configure! {:show-level? true})
  (doseq [level [:debug :info :warn :error]]
    (log/log level "<- it's" (name level)))
  (log/configure! {:show-level? false})

  ;; check level override
  (log/set-level! "huon.tests-2" :warn)
  (log/info "should see this")
  (huon.tests-2/log-info "shouldn't see this")

  ;; timing API
  (log/time "an expensive operation"
    (+ 1 1)))

(set! *main-cli-fn* main)
