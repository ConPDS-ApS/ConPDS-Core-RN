//
//  Creat.swift
//  OCR_Demo
//
//  Created by JinYC on 1/26/22.
//  Copyright © 2022 ITX Aps. All rights reserved.
//

import Foundation
import ConPDS


@objc class Creat : NSObject {
  @objc func ocrEngine(apiKey: String, licenseKey: String, path: String, success: @escaping ((String) -> Void)) {
    License.shared.getLicenseInfo(
      apiKey: apiKey,
      licenseApiKey: licenseKey)
    { (info, error) in
      if error != nil {
        return
      }
      let recognizer = RecognitionWrapper()
      let image    = UIImage(contentsOfFile: path)
      let json = try? recognizer.recognize(toJSON: image)
      success(json!)
    }
  }
}

