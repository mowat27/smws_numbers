(ns smws_numbers.views.welcome
  (:require [smws_numbers.views.common :as common]
            [noir.response :as resp])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [smws_numbers.models.distillery_codes :as distillery-codes]))

(defpage "/" [] (resp/redirect "/distilleries"))

(defpage "/distilleries" []
  (common/layout
    [:h1 "SMWS Bottling Search"]
    (form-to [:post "/distilleries/search"]
      (text-field "search")
      [:input {:id "submit-button" :type "submit" :value "search"}])))

(defpage [:post "/distilleries/search"] {bottle-number :search}
  (let [distillery (find distillery-codes/codes bottle-number)]
    (if (nil? distillery)
      (render "/distilleries/notfound" bottle-number)
      (render "/distilleries/searchresults" distillery))))

(defpage "/distilleries/searchresults" [bottle-number distillery-name]
  (common/layout
    [:h2 "Search results..."
      [:p (str bottle-number ": " distillery-name)]
      (link-to "/distilleries" "Search Again")]))

(defpage "/distilleries/notfound" {:as bottle-number}
  (common/layout
    [:h2 (str "There is no SMWS bottle number " bottle-number)]
      (link-to "/distilleries" "Search Again")))