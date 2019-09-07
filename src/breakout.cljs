(ns  breakout
  (:require [reagent.core :as r]
            ))


(def colors ["blue" "red" "yellow" "green" "orange" "white" "black"])

(def sample-state {:paddle {:x 250 :y 310 :width 30 :height 10}
                   :ball {:x 200 :y 200 :dx 1 :dy 1}
                   :speed 50
                   :bricks {}
                   :width 400
                   :height 400
                   :brickstart 50
                   :rows 5
                   
                   })

(defrecord brick [id x y width height color])


(defn generate-bricks [] 
  (let [brickwidth (/ 400 10)
        brickheight 20]
    (for [row (range 3) col (range 10)]
      (->brick (+ (* row 10) col) (* col brickwidth) (+ 50 (* row brickheight)) brickwidth brickheight (rand-nth colors))
      )

    )
  )

(defn create-initial-state []
  (assoc sample-state :bricks (generate-bricks))
  )
