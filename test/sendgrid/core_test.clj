(ns sendgrid.core-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [sendgrid.util :refer [url]]
            [environ.core :refer [env]]
            [sendgrid.alerts :refer [alerts]]
            [sendgrid.email :refer [send-email]]
            [sendgrid.stats :refer [blocks bounces
                                    invalid-emails
                                    spam-reports]]))

(deftest test-env
  "Verify that environ Variables are working"
  (is (= false (nil?  (env :api-token))))
  (is (= false (nil?  (env :from-email))))
  (is (= false (nil?  (env :to-email)))))

(deftest test-alerts
  (is (= 200 (:status (alerts {:api-token (env :api-token)})))))

(deftest test-bounces
  (is (= 200 (:status (bounces {:api-token (env :api-token)})))))

(deftest test-blocks
  (is (= 200 (:status (blocks {:api-token (env :api-token)})))))

(deftest test-invalid-emails
  (is (= 200 (:status (invalid-emails {:api-token (env :api-token)})))))

(deftest test-spam-reports
  (is (= 200 (:status (spam-reports {:api-token (env :api-token)})))))

(deftest test-email
  (is (= 202 (:status (send-email {:api-token (env :api-token)
                                   :from (env :from-email)
                                   :to (env :to-email)
                                   :subject "Test Subject"
                                   :message "Test Message"})))))
