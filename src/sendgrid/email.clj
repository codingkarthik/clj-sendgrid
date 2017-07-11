(ns sendgrid.email
  (:require [clj-http.client :as client]
            [clojure.data.json :refer [write-str]]
            [environ.core :refer [env]]
            [sendgrid.util :refer [url]]))

(defn send-email
  [{:keys [from to subject message api-token filename content]}]
  (let [json-body {:personalizations [{:to [{"email" to}]}]
                   :from    {"email" from}
                   :subject  subject
                   :content [{:type "text/html" :value  message}]}
        attachments (when (and filename content) {:attachments [{:filename filename :content content}]})
        request {:content-type :json
                 :headers {:authorization api-token}
                 :body (write-str (merge json-body attachments))}]
    (client/post (str url "mail/send") request)))
