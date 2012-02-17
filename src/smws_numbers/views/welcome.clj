(ns smws_numbers.views.welcome
  (:require [smws_numbers.views.common :as common]
            [noir.response :as resp])
  (:use [noir.core]
        [hiccup.core :only [html]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [smws_numbers.models.distillery_codes :as distillery-codes]))

(defn lookup [search-string]
  (concat
    (distillery-codes/search-by-number search-string)
    (distillery-codes/search-by-name search-string)))

(defpartial render-result [[bottle-number distillery-name]]
  (if-not (nil? bottle-number)
    [:div {:class "search-result"}
      (str "#" bottle-number " " distillery-name)]))

(defpage "/" {:keys [search-string search-results]}
  (common/layout
    (println (str "Search for (/): " search-string))
    (println (str "Found (/): " search-results))
    [:div {:id "title"}
      [:h1 "Search by SMWS number or distillery name"]]
    [:div {:id "search-form"}
      (form-to [:post "/search"]
        [:input {:id "search" :name "search" :type "text" :value search-string}]
        [:input {:id "submit-button" :type "submit" :value "Search"}])]
    [:div {:id "results"}
      (map render-result search-results)]))

(defpage [:post "/search"] {search-string :search}
  (let [search-results (lookup search-string )]
    (println (str "Search for: " search-string))
    (println (str "Found: " search-results))
    (render "/" {:search-string search-string :search-results search-results})))