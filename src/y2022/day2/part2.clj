(require '[clojure.string :as s])

(def defeats {:rock :scissor
              :scissor :paper
              :paper :rock})

(def defeated-by (into {} (map (fn [[key val]] [val key]) defeats))) 

(defn outcome->shape
  "given a player's shape and a desired outcome for the other player, what must be the other playe's shape"
  [shape outcome]
  (case outcome
    :win (shape defeated-by)
    :lose (shape defeats)
    shape))

(def play->shape {"A" :rock
                  "B" :paper
                  "C" :scissor})

(def play->outcome {"X" :lose
                    "Y" :draw
                    "Z" :win})

(def shape-score {:rock 1
                  :paper 2
                  :scissor 3})

(def outcome-score {:win 6
                    :draw 3
                    :lose 0})

(def outcome-matching {:draw :draw
                        :win :lose
                        :lose :win})

(defn plays->scores [plays]
  (let [opp-shape (play->shape (first plays))
        my-outcome (play->outcome (second plays))
        my-shape (outcome->shape opp-shape my-outcome)
        s1 (map shape-score [opp-shape my-shape])
        s2 (map outcome-score [(outcome-matching my-outcome) my-outcome])]
    (map + s1 s2)))

(let [lines (s/split (slurp "src/y2022/day2/input.txt") #"\n")]
  (->> lines
       (map #(s/split % #" "))
       (map plays->scores)
       (apply map +)
       (second)))
