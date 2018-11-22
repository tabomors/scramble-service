(ns scramble-service.handler
    (:require [ring.middleware.params :as middleware.params]
              [scramble-service.scramble :refer [scramble?]]))

(defmulti router :uri)

(defmethod router "/scramble"
  [req]
  (let [str1 (get-in req [:query-params "str1"])
        str2 (get-in req [:query-params "str2"])
        res  (scramble? str1 str2)]
   {:status 200
    :headers {"Content-Type" "application/json"}
    :body (str "{\"result\":" res "}")}))

(defmethod router :default
  [req]
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body "<h1>Not found</h1>"})

(defn app [req]
  ((middleware.params/wrap-params router) req))

