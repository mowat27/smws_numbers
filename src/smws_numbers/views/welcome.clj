(ns smws_numbers.views.welcome
  (:require [smws_numbers.views.common :as common]
            [noir.content.getting-started]
            [noir.response :as resp])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [smws_numbers.models.distillery_codes :as distillery-codes]))

(defpage "/welcome" []
  (common/layout
    [:p "Welcome to smws_numbers"]))

(defpage "/distilleries" []
  (common/layout
    [:h1 "Search for Distilleries"]
    (form-to [:post "/distilleries/search"]
      (label "search-string" "Type the name of a Distillery or a SMWS bottling number below")
      (text-field "search-string")
      (submit-button "Submit"))))

(defpage "/distilleries/searchresults" [bottle-number distillery-name]
  (common/layout
    [:h2 "Search results..."
      [:p (str bottle-number ": " distillery-name)]
      (link-to "/distilleries" "Search Again")]))

(defpage [:post "/distilleries/search"] {bottle-number :search-string}
  (let [distillery (find distillery-codes/codes bottle-number)]
    (render "/distilleries/searchresults" distillery)))