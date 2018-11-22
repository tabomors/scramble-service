(ns scramble-service.core
  (:require [scramble-service.app :refer [app]]
            [reagent.core :as reagent]))

(defn mount-root [] (reagent/render [app] (.getElementById js/document "app")))

(defn ^:export init [] (mount-root))