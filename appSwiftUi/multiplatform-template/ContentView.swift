//
//  ContentView.swift
//  multiplatform-template
//
//  Created by Luca Spinazzola on 7/12/19.
//  Copyright © 2019 lucaspinazzola. All rights reserved.
//

import SwiftUI
import app
import URLImage
import Grid

struct ContentView : View {
    @ObservedObject private var vm = RickAndMortyVmObserver()
    
    var body: some View {
        ScrollView {
            Grid(vm.imgs, id: \.self) { img in
                URLImage(URL.init(string: img.url)!,
                         placeholder: {
                             ProgressView($0) { progress in
                                 ZStack {
                                     if progress > 0.0 {
                                         CircleProgressView(progress).stroke(lineWidth: 8.0)
                                     }
                                     else {
                                         CircleActivityView().stroke(lineWidth: 50.0)
                                     }
                                 }
                             }
                                 .frame(width: 50.0, height: 50.0)
                         },
                         content: {
                             $0.image
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .clipShape(RoundedRectangle(cornerRadius: 5))
                                .padding(.leading, 5.0)
                                .padding(.trailing, 5.0)
                                 .shadow(radius: 2.0)
                         })
            }
        }.gridStyle(
            ModularGridStyle(columns: 2, rows: .fixed(200))
        )
    }
}

#if DEBUG
struct ContentView_Previews : PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
