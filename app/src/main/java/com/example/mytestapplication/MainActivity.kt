package com.example.mytestapplication

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.mytestapplication.databinding.LayoutActivityMainBinding
import kotlinx.coroutines.Runnable
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : ComponentActivity() {

    private lateinit var binding: LayoutActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            println("Net Button Pressed...")
            sendMessage(binding.edtSendMessage.text.toString())
        }
    }

    private fun sendMessage(msg: String) {
        val handler = Handler()
        val thread = Thread(Runnable {

            try {
                val socket = Socket(IP_NUMB, PORT_NUMB)
                if (socket != null) {
                    val outPutStream = socket.getOutputStream()
                    if (outPutStream != null) {
                        val printOutput = PrintWriter(outPutStream)
                        if (printOutput != null) {
                            println("Net Socket $socket")
                            printOutput.println(msg)
                            printOutput.flush()
                            val bufferedReader =
                                BufferedReader(InputStreamReader(socket.getInputStream()))
                            val str = bufferedReader.readLine()
                            handler.post(Runnable {
                                val s = binding.tvReplyFromServer.text.toString()
                                if (s.trim().length != 0) {
                                    binding.tvReplyFromServer.text = s + "\nFrom Server : " + str
                                }
                            })
                            printOutput.close()
                            outPutStream.close()
                            socket.close()
                        } else {
                            println("failed to create print out put ")
                        }
                    } else {
                        println("Failed to create output stream")
                    }
                } else {
                    println("Filed to Create Socket")
                }
            } catch (exe: Exception) {
                Log.e(TAG, "Exception Occurred" + exe.message)
            }
        })
        thread.start()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        val TAG = MainActivity.Companion::class.java.classes.toString()
        val IP_NUMB = "192.168.29.88"
        val PORT_NUMB = 5000
    }
}


