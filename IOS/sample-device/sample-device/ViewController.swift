//
//  ViewController.swift
//  sample-device
//
//  Created by George Vigelette on 12/9/18.
//  Copyright © 2018 George Vigelette. All rights reserved.
//

import UIKit
import edgesdk
import Foundation

class ViewController: UIViewController {
    
    @IBOutlet weak var btnConnect: UIButton!
    @IBOutlet weak var btnDisconnect: UIButton!
    
    @IBOutlet weak var lblSent: UILabel!
    @IBOutlet weak var lblGood: UILabel!
    @IBOutlet weak var lblBad: UILabel!
    @IBOutlet weak var lblRcvd: UILabel!
    @IBOutlet weak var lblLastTemp: UILabel!
    @IBOutlet weak var lblLastHum: UILabel!
    @IBOutlet weak var lblLastRcvd: UILabel!
    @IBOutlet weak var lblLastSent: UILabel!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    /*  the below is the result from the RegisterDevice call that returns the below json structure the msg contains the token
     *
     *   {
     *       "KEY": "",
     *       "MSG": ""
     *   }
     *
     *
     * */
    let _demoKey:String = "JLRYm8h68K5oNw4QnzA4QHIjYEA7hQNe2jEQJJf3t81/UdyaCSaRyqasFKtzJJK/+8snZKdMU/PJFt979vZG7M/srwRyEHa5yFKtJQrJ1d6cjWwFUtf4QMvRmJn6PJTWXntqAzDmg6TYYcfOtCPbXzW4KvACzFEJYFwV1S81HQY="
    
    let _demoToken:String = "yJWwbJJHgqOqJqGak+OePvFMquWvgOD7GmE29aPqAexvarUTbxpLSlwgtzDyfOEyrU+2YNj2CT8PXcaZK79uqvmkjQzjm12ggcfbyk6i8fW98WKMLQdSCgElKYB7vD9I75uWIMNRBNYgy2LFx43xgImIj5+EjU8PQOdakLw/x066nj5L3EQaT9ebTIMnD2piJ5ObW9kWUYHofxrIO8Ls0Vbj1R5jdXbkza2Oe2Y/HsmGIqdZ6mcRFg84mRmnhTxRR6xRntAu/CquOIEVDWIJU+BzzED4LhHfdBEYfluwCdgZ0SY5vCdOsbW5kv3BExXv6sxOthqSoIoEiJb/T0fynn6yudcjLQ7P9k+ffneEukMyIVpOFFJL3ZUuwf0W3vy8pwwlGy9MGdPHeCdvY07UGA=="
 /*
     let _demoToken:String = "yJWwbJJHgqOqJqGak+OePvFMquWvgOD7GmE29aPqAexvarUTbxpLSlwgtzDyfOEyrU+2YNj2CT8PXcaZK79uql4OEbhjqeOQEkzX1jz/GkMed1JB5qsXWFuD7kFKsyFkgwm0Wm2bwpA3S7tKGjmZLk2oJ6UVmUp/fusOz4iDimK9m2mWfjxUwqXKk9Yn2V9WxTD5q+CKbRrUW9IujAZE9x1ZDDKFbPqkzeglnmwF1rrRSusFK+BBXmQISj5q4/nEm9CffVsIUCJb8bxuHToS0Co77srKQm8WbWBHBlvMHgtq1WIYaLGxoOlSsxbfhlPs5GWGW+YLjkcK+2A349PylX6vUlRAS5lPkcz+Kigeyx4=="
     */
    
    // Edgestream Client instance
    let _client:EdgestreamClient
    
    
    var cntSent = 0
    var cntGood: Int = 0
    var cntBad = 0
    var cntRcvd = 0
    var randomTelem : String!
    
    required init?(coder aDecoder: NSCoder) {
        print("init coder style")
        _client = EdgestreamClient()
        super.init(coder: aDecoder)
        if((_client.isRegistered)){
            print("registered")
        }else{
            print("not registered")
            
            if(!_client.setToken(key: _demoKey, deviceToken: _demoToken)){
                print("Failed to set token")
                return
            }else{
                print("Set token")
            }
        }
    }
    
    // Timers used to control message and polling rates
    var timerMsgRate: Timer!
    // var timerDoWork: Timer!
    
    /// Increments the messages sent count and updates the UI
    func incrementSent() {
        cntSent += 1
        lblSent.text = String(cntSent)
    }
    
    /// Increments the messages successfully received and updates the UI
    func incrementGood() {
        cntGood += 1
        lblGood.text = String(cntGood)
    }
    
    /// Increments the messages that failed to be transmitted and updates the UI
    func incrementBad() {
        cntBad += 1
        lblBad.text = String(cntBad)
    }
    
    func incrementRcvd() {
        cntRcvd += 1
        lblRcvd.text = String(cntRcvd)
    }
    
    
    /// Display an error message
    ///
    /// parameter message: The message to display
    /// parameter startState: Start button will be set to this state
    /// parameter stopState: Stop button will be set to this state
    func showError(message: String, startState: Bool, stopState: Bool) {
        btnConnect.isEnabled = startState
        btnDisconnect.isEnabled = stopState
        print(message)
    }
    
    @objc func SendTelemetryData() {
        var dataString: String!
        
        let temperature = String(format: "%.2f",drand48() * 15 + 20)
        let humidity = String(format: "%.2f", drand48() * 20 + 60)
        let data : [String : String] = ["deviceId":_client.getDeviceId,
                                        "temperature":temperature,
                                        "humidity": humidity]
        
        lblLastTemp.text = temperature
        lblLastHum.text = humidity
        
        // This the message
        dataString = data.description
        lblLastSent.text = dataString
        
        if(!_client.sendData(dictString: dataString)){
            // failed call
            print("failed send data call of Edgestreamclient SDK")
        }else{
            incrementSent()
        }
        
    }
    
    
    /// Called when the start button is clicked on the UI. Starts sending messages.
    ///
    /// - parameter sender: The clicked button
    @IBAction func connectCient(sender: UIButton!) {
        
        // Dialog box to show action received
        btnConnect.isEnabled = false
        btnDisconnect.isEnabled = true
        cntSent = 0
        lblSent.text = String(cntSent)
        cntGood = 0
        lblGood.text = String(cntGood)
        cntBad = 0
        lblBad.text = String(cntBad)
        
        // check if device is registered with Edgestream
        if(!_client.isRegistered){
            // Register device via web api first and provide key and token string to setToken function
            if(!_client.setToken(key: _demoKey, deviceToken: _demoToken)){
                // failed to set token update ui and return
                btnConnect.isEnabled = true
                btnDisconnect.isEnabled = false
                return
            }
            
        }
        
        if(!_client.iotConnect()){
            // failed to connect to Edgespace cloud HUB
            // update ui buttons and message
            btnConnect.isEnabled = true
            btnDisconnect.isEnabled = false
            return
        }
        
        // connected start data sender on timer
        timerMsgRate = Timer.scheduledTimer(timeInterval: 5, target: self, selector: #selector(SendTelemetryData), userInfo: nil, repeats: true)
    }
    
    /// Called when the stop button is clicked on the UI. Stops sending messages and cleans up.
    ///
    /// - parameter sender: The clicked button
    @IBAction public func disconnectClient(sender: UIButton!) {
        
        timerMsgRate?.invalidate()
        btnConnect.isEnabled = true
        btnDisconnect.isEnabled = false
        if(!_client.iotDisconnect()){
            print("Failed to disconnect")
        }
    }
}

