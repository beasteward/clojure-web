;
(ns guestbook.routes.app
  (:require
   [spec-tools.data-spec :as ds]
   #?@(:clj [[guestbook.layout :as layout]
             [guestbook.middleware :as middleware]]
       :cljs [[guestbook.views.home :as home]
              [guestbook.views.author :as author]
              [guestbook.views.tag :as tag]
              [guestbook.views.feed :as feed]
              [guestbook.views.profile :as profile]
              [guestbook.views.post :as post]])))
;;...
;
#?(:clj
   (defn home-page [request]
     (layout/render
      request
      "home.html")))

;
(defn app-routes []
  [""
   #?(:clj {:middleware [middleware/wrap-csrf]
            :get home-page})
   ;
   ;; require [spec-tools.data-spec :as ds]

   ["/"
    (merge
     {:name ::home}
     #?(:cljs
        {:parameters {:query {(ds/opt :post) pos-int?}}
         :controllers home/home-controllers
         :view #'home/home}))]
   ;
   ["/my-account/edit-profile"
    (merge
     {:name ::profile}
     #?(:cljs
        {:controllers profile/profile-controllers
         :view #'profile/profile}))]
   ;
   ;;...
   ["/user/:user"
    (merge
     {:name ::author}
     #?(:cljs {:parameters {:query {(ds/opt :post) pos-int?}
                            :path {:user string?}}
               :controllers author/author-controllers
               :view #'author/author}))]
   ;
   ["/tag/:tag"
    (merge
     {:name ::tag}
     #?(:cljs {:parameters {:query {(ds/opt :post) pos-int?}
                            :path {:tag string?}}
               :controllers tag/tag-controllers
               :view #'tag/tag}))]
   ;
   ["/feed"
    (merge
     {:name ::feed}
     #?(:cljs {:parameters {:query {(ds/opt :post) pos-int?}}
               :controllers feed/feed-controllers
               :view #'feed/feed}))]
   ;
   ;
   ["/post/:post"
    (merge
     {:name ::post}
     #?(:cljs {:parameters {:query {(ds/opt :reply) pos-int?}
                            :path {:post pos-int?}}
               :controllers post/post-controllers
               :view #'post/post-page}))]
   ;
   ])
;
