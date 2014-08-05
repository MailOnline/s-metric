(ns s-metric.protocols)

(defprotocol Score
  (match [_ s1 s2])
  (best-score [_ s1 s2])
  (match-% [_ s1 s2]))
