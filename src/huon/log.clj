(ns huon.log)

(defn- src-meta [form]
  {:ns *ns*, :line (:line (meta form))})

(defmacro log [level & msg]
  "Evaluate and log `msg` if configured level >= `level`."
  (let [{:keys [ns line]} (or (::src (meta &form)) (src-meta &form))]
    `(log* ~(str ns)
           ~level
           ~(format "[%s:%s]" ns line)
           (str "[" (clojure.string/upper-case (name ~level)) "]")
           (fn [] (list ~@msg)))))

(defmacro debug
  "Evaluate and log `msg` if configured level >= `:debug`"
  [& msg]
  (with-meta `(log :debug ~@msg) {::src (src-meta &form)}))

(defmacro info
  "Evaluate and log `msg` if configured level >= `:info`"
  [& msg]
  (with-meta `(log :info ~@msg) {::src (src-meta &form)}))

(defmacro warn
  "Evaluate and log `msg` if configured level >= `:warn`"
  [& msg]
  (with-meta `(log :warn ~@msg) {::src (src-meta &form)}))

(defmacro error
  "Evaluate and log `msg` if configured level >= `:error`"
  [& msg]
  (with-meta `(log :error ~@msg) {::src (src-meta &form)}))
