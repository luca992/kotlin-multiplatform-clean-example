//
//  ContentView.swift
//  multiplatform-template
//
//  Created by Luca Spinazzola on 7/12/19.
//  Copyright © 2019 lucaspinazzola. All rights reserved.
//

import SwiftUI
import app

struct ContentView : View {
    @State private var imgs = "NONE"
    var body: some View {
        Text(imgs)
    }
}

#if DEBUG
struct ContentView_Previews : PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
