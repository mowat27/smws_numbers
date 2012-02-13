(ns smws_numbers.views.welcome
  (:require [smws_numbers.views.common :as common]
            [noir.response :as resp])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [smws_numbers.models.distillery_codes :as distillery-codes]))

(defn lookup [search-string]
  (find distillery-codes/codes search-string))

(defpartial search-result [{:keys [bottle-number distillery-name]}]
  (if-not (nil? bottle-number)
    [:div {:class "search-result"}
      [:p (str "#" bottle-number " " distillery-name)]]))

(defpage "/" {:as params}
  (let [bottle-number (first params) distillery-name (second params)]
  (common/layout
    [:h1 "SMWS Bottling Search"]
    (form-to [:post "/search"]
      [:input {:id "search" :name "search" :type "text" :value bottle-number}]
      [:input {:id "submit-button" :type "submit" :value "search"}])
    (search-result {:bottle-number bottle-number :distillery-name distillery-name}))))

(defpage [:post "/search"] {bottle-number :search}
  (let [distillery (lookup bottle-number)]
    (println (str "Search for: " bottle-number))
    (render "/" distillery)))