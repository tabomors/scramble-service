(ns scramble-service.handler
    (:require [ring.middleware.params :as middleware.params]
              [ring.middleware.resource :as middleware.resource]
              [scramble-service.scramble :refer [scramble?]]))

(def home-page-path "public/index.html")

(defmulti router :uri)

(defmethod router "/"
  [req]
  (middleware.resource/resource-request req home-page-path))

(defmethod router "/status"
  [req]
  {:status 200
   :body "Scramble service is up"})

(defmethod router "/scramble"
  [req]
  (let [str1 (get-in req [:query-params "str-1"])
        str2 (get-in req [:query-params "str-2"])
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
  (let [router' (-> router
                    middleware.params/wrap-params
                    (middleware.resource/wrap-resource "public"))]
  (router' req)))
