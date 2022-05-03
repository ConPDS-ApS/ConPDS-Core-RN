/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { useState } from 'react';
import {
  Image,
  Pressable,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  Text,
  View,
  ScrollView,
  NativeModules
} from 'react-native';
import {launchImageLibrary} from 'react-native-image-picker';

const App = () => {
  const { OcrModule } = NativeModules;
  const [selecteImg, setSelectedImg] = useState(null);
  const [result, setResult] = useState("OCR Result");
  const handlePickerClick = async () => {
    const {didCancel, errorCode, errorMessage, assets} = await launchImageLibrary({
      mediaType: 'photo'
    });
    if (didCancel) {
      return;
    }
    if (errorMessage || errorCode) {
      console.log('image picker error msg:', errorMessage, 'code:', errorCode);
      alert(errorMessage);
    } else {
      console.log(assets);
      OcrModule.getOcrDatafromImage(
        assets[0].base64, 
        assets[0].fileName, 
        assets[0].fileSize, 
        assets[0].type, 
        assets[0].uri,
        (return_data) => {
          console.log('|||||||||||||||||||||', return_data);
          setResult(return_data);
        }
      );
      setSelectedImg(assets[0]);
    }
  }
  return (
    <>
      <SafeAreaView style={styles.topSafeArea}/>
        <SafeAreaView style={styles.bottomSafeArea}>
          <StatusBar barStyle={'light-content'} backgroundColor={'blue'}/>
          <View style={styles.container}>
            <View style={styles.header}>
              <Text style={styles.headerTxt}>OCR Engine</Text>
            </View>
            <View style={styles.content}>
              <ScrollView>
                <Text style={styles.descriptionTxt}>
                  Selected Image
                </Text>
                <Pressable style={styles.pickerView} onPress={handlePickerClick}>
                  {selecteImg ? 
                    <Image source={{uri: selecteImg.uri}} resizeMode='cover' style={styles.imgView}/>
                  :
                    <Text style={[styles.descriptionTxt, {width: 150}]}>
                      Please choose a Image (Tab here)
                    </Text>
                  }
                </Pressable>
                <Text style={styles.descriptionTxt}>
                  {result}
                </Text>
              </ScrollView>
            </View>
          </View>
        </SafeAreaView>
      </>
  );
};

const styles = StyleSheet.create({
  topSafeArea: {
    flex: 0, 
    backgroundColor: 'blue'
}, 
bottomSafeArea: {
    flex: 1, 
    backgroundColor: 'white'
},
  container: {
    height: '100%',
  },
  header: {
    backgroundColor: 'blue',
    height: 50,
    width: '100%',
    paddingLeft: 16,
    justifyContent: 'center'
  },
  headerTxt: {
    color: 'white',
    fontWeight: '700',
    fontSize: 16
  },
  content: {
    flex: 1,
    padding: 16,
    shadowColor: 'black',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.4,
    shadowRadius: 3,
  },
  descriptionTxt: {
    fontSize: 14,
    textAlign: 'center',
    color: 'black',
  },
  pickerView: {
    height: 250,
    borderRadius: 5,
    backgroundColor: 'white',
    marginVertical: 16,
    alignItems:'center',
    justifyContent: 'center',
    overflow:'hidden',
    elevation: 4
  },
  imgView: {
    flex: 1,
    width: '100%',
  }
});

export default App;
