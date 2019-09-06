(ns  breakout
  (:require [reagent.core :as r]
            ))


(def colors ["blue" "read" "yellow" "green" "violet" "ivory" ])

(def sample-state {:paddle {}
                   :speed 50
                   :bricks {}
                   :width 300
                   :height 300
                   :brickstart 50
                   :rows 5
                   
                   })

(defrecord brick [id x y width height color])


(defn generate-bricks [] 
  (let [brickwidth (/ 300 20)
        brickheight 40]
    (for [row (range 3) col (range 15)]
      (->brick (+ (* row 3) col) (* col brickwidth) (+ 50 (* row brickheight)) brickwidth brickheight (rand-nth colors))
      )

    )
  )

(defn create-initial-state []
  (assoc sample-state :bricks (generate-bricks))
  )
