(ns huon.log)

(defmacro ^:private log [level args]
  (let [origin-meta# (-> &form meta :origin)
        origin#      (str (:ns origin-meta#) ":" (:line origin-meta#))]
    `(log* ~(str *ns*)
           ~level
           #(str "[" ~origin# "] "
                 "[" (clojure.string/upper-case (name ~level))  "] "
                 (clojure.string/join " " ~(mapv (fn [x] `(str ~x)) args))))))

(defn- origin-meta [form]
  {:origin {:ns *ns* :line (:line (meta form))}})

(defmacro debug
  "Evaluate and log args if level >= :debug"
  [& args]
  (with-meta `(log :debug ~args) (origin-meta &form)))

(defmacro info
  "Evaluate and log args if level >= :info"
  [& args]
  (with-meta `(log :info ~args) (origin-meta &form)))

(defmacro warn
  "Evaluate and log args if level >= :warn"
  [& args]
  (with-meta `(log :warn ~args) (origin-meta &form)))

(defmacro error
  "Evaluate and log args if level >= :error"
  [& args]
  (with-meta `(log :error ~args) (origin-meta &form)))
