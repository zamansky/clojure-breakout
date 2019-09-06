(ns canvas)

(defn get-canvas [component]
  "Get a canvas from a canvas reagent component"
  (r/dom-node component))

(defn get-ctx [canvas]
  (.getContext canvas "2d")
  )


(defn rect [ctx x y w h color bordercolor]
  (set! ctx -strokeStyle bordercolor)
  (set! ctx -fillStyle color)
  (.fillRect x y w h)
  )
