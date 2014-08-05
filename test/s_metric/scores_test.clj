(ns s-metric.scores-test
  (:require [midje.sweet :refer :all]
            [s-metric.scores :as s]))

(defn rand-str [n]
  (.substring (clojure.string/replace (java.util.UUID/randomUUID) #"-" "") 0 n))

(facts "bulk matching"
       (let [target (into [] (map (fn [n] (rand-str 10)) (range 10000)))]
         (fact "it only returns the top n"
               (time (count (s/top-scores "f11k45k" target 20))) => 20)))
