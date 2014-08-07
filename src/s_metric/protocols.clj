(ns s-metric.protocols)

(defprotocol Score
  (match [_ s1 s2])
  (best-score [_ s1 s2])
  (match-% [_ s1 s2]))

(defn match-%* [_ s target]
  "Invokes specific impementations of match and best-score to
  transform them into a percentage rapresentation with 2 decimals."
  (let [match (match _ s target)
        best (best-score _ s target)]
    (double (/ (Math/round (* (/ match best) 10000.)) 100))))

(def ScoreImpl
  {:match-% #'match-%*})
