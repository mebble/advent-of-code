(require '[clojure.string :as s])

(def defeats {:rock :scissor
              :scissor :paper
              :paper :rock})

(def play->shape {"A" :rock
                  "B" :paper
                  "C" :scissor
                  "X" :rock
                  "Y" :paper
                  "Z" :scissor})

(def shape-score {:rock 1
                  :paper 2
                  :scissor 3})

(def outcome-score {:win 6
                    :draw 3
                    :lose 0})

(defn play-round [[l-shape r-shape]]
  (cond
    (= l-shape r-shape)           [:draw :draw]
    (= (l-shape defeats) r-shape) [:win :lose]
    :else                         [:lose :win]))

(defn plays->scores [plays]
  (let [shapes (map play->shape plays)
        s1 (map shape-score shapes)
        s2 (map outcome-score (play-round shapes))]
    (map + s1 s2)))

(let [lines (s/split (slurp "2022/day2/input.txt") #"\n")]
  (->> lines
       (map #(s/split % #" "))
       (map plays->scores)
       (apply map +)
       (second)))
