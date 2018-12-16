(ns scramble-service.validators)

(defn error? [val]
  (boolean (:error val)))

(defn only-chars? [value]
  (boolean (re-matches #"^[A-Za-z]*$" value)))

(defn not-empty? [value]
  (and (not (nil? value)) (not (clojure.string/blank? value))))

(defn validate
  ([value validator]
   (validator value))
  ([value validator error-message]
   (or (validate value validator) error-message)))
