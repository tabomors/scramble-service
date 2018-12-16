(ns scramble-service.app
  (:require
   [ajax.core :as ajax]
   [reagent.core :as r]
   [scramble-service.validators :as validators]))

;; Helpers

(def required-fields #{:str-1 :str-2})

(defn validate-scramble-field [value]
  (or (and
       (validators/validate value validators/not-empty?)
       (validators/validate value validators/only-chars?))
      {:error "Only chars are allowed"}))

(defn form-valid?
  [form-fields]
  (empty? (filter (comp validators/error? validate-scramble-field :value #(% form-fields)) required-fields)))

(defn submit-disabled?
  [form-fields]
  (let [required-form-values (map (comp :value #(% form-fields)) required-fields)
        not-empty? (complement empty?)]
    (not-empty? (filter clojure.string/blank? required-form-values))))

;; State

(def state (r/atom {:form-fields {}}))

;; Views

(defn form-field-error
  [field]
  (when (validators/error? field)
    [:p (:error field)]))

(defn form-field
  [{:keys [name type label] :or {type "text"}}]
  (let [field @(r/cursor state [:form-fields name])]
    [:div.form-field
     [:label {:for name} label]
     [:input {:type type
              :id name
              :name name
              :on-change (fn [e]
                           (let [value (-> e .-target .-value)]
                             (swap! state update-in [:form-fields name]
                                    (fn [field-data]
                                      (-> field-data
                                          (assoc :value value)
                                          (assoc :error (:error (validate-scramble-field value))))))))
              :value (:value field)}]
     [form-field-error field]]))

(defn form []
  [:form
   {:on-submit (fn [e]
                 (.preventDefault e)
                 (let [form-fields (:form-fields @state)
                       query-params {:str-1 (-> form-fields :str-1 :value clojure.string/lower-case )
                                     :str-2 (-> form-fields :str-2 :value clojure.string/lower-case)}]
                   (ajax/GET "/scramble" {:params query-params
                                          :handler (fn [res] (swap! state assoc :scramble-result {:res (get res "result")}))
                                          :error-handler (fn [res] (swap! state assoc :scramble-result {:error res}))
                                          })))}
   [form-field {:name :str-1
                :label "First string"}]
   [form-field {:name :str-2
                :label "Second string"}]
   [:div.form-field
    [:button
     {:type "submit"
      :disabled (not (form-valid? (:form-fields @state)))}
     "Presse to \"scramble\""]]
   (when-let [scramble-result (:scramble-result @state)] 
     (if-not (validators/error? scramble-result) 
       [:p (str "Scramble result: " (:res scramble-result))]
       [:p (str "Something went wrong. Try later")]))
   ])

(defn app [] [form])
