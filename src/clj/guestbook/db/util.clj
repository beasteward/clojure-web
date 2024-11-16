;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
;
(ns guestbook.db.util
  (:require [clojure.string :as string]))

(defn tag-regex [tag]
  (when-not (re-matches #"[-\w]+" tag)
    (throw (ex-info "Tag must only contain alphanumeric characters!"
                    {:tag tag})))
  (str "'.*(\\s|^)#"
       tag
       "(\\s|$).*'"))
;


(defn tags-regex [tags-raw]
  (let [tags (filter #(re-matches #"[-\w]+" %) tags-raw)]
    (when (not-empty tags)
      (str "'.*(\\s|^)#("
           (string/join "|" tags)
           ")(\\s|$).*'"))))
