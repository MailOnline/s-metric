(ns s-metric.scores-test
  (:require [midje.sweet :refer :all]
            [s-metric.levenshtein]
            [s-metric.scores :as s])
  (:import [s_metric.levenshtein LevenshteinDistance]))

(defn rand-str [n]
  (.substring (clojure.string/replace (java.util.UUID/randomUUID) #"-" "") 0 n))

(facts "bulk matching"
       (let [target (into [] (map (fn [n] (rand-str 10)) (range 10000)))]
         (fact "changing the combination of algos"
               (time (count (s/top-scores "f11k45k" target 20 (LevenshteinDistance.)))) => 20)))
