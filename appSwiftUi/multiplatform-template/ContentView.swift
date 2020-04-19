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
    @ObservedObject private var vm = RickAndMortyVmObserver()
    
    var body: some View {
        List(vm.imgs, id: \.self) { img in
            Text(img.url)
        }
    }
}

#if DEBUG
struct ContentView_Previews : PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
