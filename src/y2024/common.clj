(ns y2024.common)

(defn parse-int [s]
  (Integer/parseInt s))

(defn remove-at [i items]
    (into (subvec items 0 i) (subvec items (inc i))))
