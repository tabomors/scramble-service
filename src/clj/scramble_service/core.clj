(ns scramble-service.core
  (:require 
   [ring.adapter.jetty :as jetty]
   [scramble-service.handler :refer [app]])
  (:gen-class))

(comment
  (def server (jetty/run-jetty #'app {:port 3000 :join? false}))
  (.stop server)
  )



(defn -main
  [& args]
  (jetty/run-jetty app {:port 3000 :join? false}))
