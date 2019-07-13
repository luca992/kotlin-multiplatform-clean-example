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
    let names = NamesSupplier().listOfNames
    var body: some View {
        Text(names[0])
    }
}

#if DEBUG
struct ContentView_Previews : PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
