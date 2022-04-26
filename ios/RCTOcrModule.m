//
//  RCTOcrModule.m
//  OCR_Demo
//
//  Created by Dev 209 on 1/21/22.
//

#import <Foundation/Foundation.h>
#import <React/RCTLog.h>
#import "RCTOcrModule.h"

@implementation RCTOcrModule

// To export a module named RCTOcrModule
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(getOcrDatafromImage:(NSString *)base64
                  fileName:(NSString *)fileName
                  fileSize:(double *)fileSize
                  type:(NSString *)type
                  uri:(NSString *)uri
                  myCallback:(RCTResponseSenderBlock)callback)
{
  NSString* filePath = [uri stringByReplacingOccurrencesOfString:@"file://" withString:@""];

  Creat* create = [[Creat alloc] init];
  [create ocrEngineWithApiKey:@"YOUR_IOS_API_KEY" licenseKey:@"YOUR_IOS_LICENSE_KEY" path:filePath success:^(NSString * result) {
    callback(@[result]);
  }];
}


@end

