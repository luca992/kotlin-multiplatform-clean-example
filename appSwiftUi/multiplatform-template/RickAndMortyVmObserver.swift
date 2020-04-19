//
//  RickAndMortyVmObserver.swift
//  multiplatform-template
//
//  Created by Luca Spinazzola on 4/18/20.
//  Copyright © 2020 lucaspinazzola. All rights reserved.
//

import SwiftUI
import app

class RickAndMortyVmObserver: ObservableObject {
    init() {
        let rmApi = RickAndMortyApi.init(enableLogging: true)
        let db = DatabaseCompanion.init().invoke(driver: DatabaseProviderKt.doInitSqldelightDatabase())
        let imgDbHelper = ImgDbHelperImpl(database: db)
        let imgMapper : ImgMapper = ImgMapperImpl()
        let repo = RickAndMortyRepositoryImpl(api: rmApi,
                                              imgDbHelper: imgDbHelper,
                                              imgMapper: imgMapper)
        let getCharacterImgsAndListenForUpdatesUseCase = GetCharacterImgsAndListenForUpdatesUseCase.init(rickAndMortyRepository: repo)
        let updateCharacterImgsUseCase = UpdateCharacterImgsUseCase.init(rickAndMortyRepository: repo)
        let vm = RickAndMortyCharactersViewModel.init(
            getCharacterImgsAndListenForUpdatesUseCase: getCharacterImgsAndListenForUpdatesUseCase,
            updateCharacterImgsUseCase: updateCharacterImgsUseCase)
        vm.imgs.addObserver { [weak self] urls in
            self?.imgs = urls as! [Img]
        }
    }





    
    @Published var imgs : [Img] = []
    
    
}
