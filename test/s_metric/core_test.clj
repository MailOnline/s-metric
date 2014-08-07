(ns s-metric.core-test 
  (:require [s-metric.core :as metric]
            [s-metric.bulk :as bulk]))

;;;; AUTOCORRECTION

;; A simple dictionary (in reality this would be something
;; like the list of commonly mispelled words)
(def dictionary ["balance"
                 "battalion"
                 "beginning"
                 "belief"
                 "believe"
                 "beneficial"
                 "benefited"
                 "boundaries"
                 "Britain"
                 "business"])

;; returns the score and the best match in the dictionary, 
;; such as [71.43 "believe"]
(bulk/mispelled "beleive" dictionary)

;; if you need more control you can access the top 20 scores
;; ordered from the lowest to the highest
(bulk/top-scores "buisness" dictionary)


;;;; SINGLE SCORE


;; naive works better for substrings "affinity"
;; => 66.07
(metric/naive "ACACACTA" "AGCACACA")

;; hamming is more about editing distance
;; and performs lower on DNA-like sequencing
;; => 25.0
(metric/hamming "ACACACTA" "AGCACACA")

;; levenshtein also considers deletion and insertions
;; so it performs good with substrings as well
;; => 75.0
(metric/levenshtein "ACACACTA" "AGCACACA")
