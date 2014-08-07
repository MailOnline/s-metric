(ns s-metric.bulk-test
  (:require [midje.sweet :refer :all]
            [s-metric.levenshtein]
            [s-metric.bulk :as b])
  (:import [s_metric.levenshtein LevenshteinDistance]))

(defn rand-str [n]
  (.substring (clojure.string/replace (java.util.UUID/randomUUID) #"-" "") 0 n))

(facts "bulk matching"
       (let [target (into [] (map (fn [n] (rand-str 12)) (range 10000)))]
         (fact "changing the combination of algos"
               (time (count (b/top-scores "f11k45kxREE" target 20 (LevenshteinDistance.)))) => 20)))
