(ns main
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <! go go-loop timeout)]
            [breakout :as b]
            ))


(defn draw-cells [component state]
  (let [canvas (r/dom-node component)
        ctx (.getContext canvas "2d")
        ]
    (set! (. ctx -fillStyle) (str "rgb(" 255 "," 255"," 255 ")"))
    (.fillRect ctx 0 0 300 300)
    
    (set! (. ctx -fillStyle) (str "rgb(" 0 "," 255"," 0 ")"))
    (if (:target @state)
      (let [ [x y] (:target @state)] (.fillRect ctx (* 10 x) (* 10 y) 10 10)))
    (set! (. ctx -fillStyle) (str "rgb(" 255 "," 0"," 0 ")"))
    (doseq [ [x y] (:body @state)]
      (.fillRect ctx (* 10 x) (* 10 y) 10 10)                                 
      )

    )
  )


(defn canvas [state]
  (let [state state]
    (r/create-class
     {:display-name "canvas"
      :component-did-mount #(draw-cells % state) 
      :component-did-update #(draw-cells % state)
      :reagent-render (fn  []
                        @state
                        [:canvas { :id "c" :width 300 :height 300 :style {:border "2px solid green"}}])
      }
     )))



(defn main-component []
  [:div 
   [:h1 "This is the component"]
   ])


(defn mount [c]
  (r/render-component [c] (.getElementById js/document "app"))
  )

(defn reload! []
  (mount main-component)
  (print "Hello reload!"))

(defn main! []
  (mount main-component)
  (print "Hello Main"))
