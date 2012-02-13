(ns smws_numbers.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "smws_numbers"]
               (include-css "/css/reset.css")
               (include-css "/css/layout.css")]
              [:body
               [:div#wrapper
                content]]))
