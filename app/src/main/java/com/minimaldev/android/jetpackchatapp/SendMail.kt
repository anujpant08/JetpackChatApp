package com.minimaldev.android.jetpackchatapp

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.activation.DataHandler
import javax.mail.*
import javax.mail.event.ConnectionEvent
import javax.mail.event.ConnectionListener
import javax.mail.event.TransportEvent
import javax.mail.event.TransportListener
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.util.ByteArrayDataSource


class SendMail() : Authenticator(), TransportListener, ConnectionListener{
    companion object{
        private val TAG : String = "SendMail"
        private lateinit var session : Session
        private var body : String = "The code for login is: "
        private val subject : String = "Verification code for JetChat"
    }
    private lateinit var code : String
    private lateinit var context: Context
    private var toAddress : String = ""
    private val fromAddress = "minimaldev4playstore@gmail.com"
    suspend fun send(toAddress : String, code : String, context : Context) {
        this.context = context
        this.toAddress = toAddress
        this.code = code
        body += this.code
        val properties : Properties = Properties()
        properties.put(key = "mail.smtp.host", value = "smtp.gmail.com")
        properties.put(key = "mail.smtp.socketFactory.port", value = "465")
        properties.put(key = "mail.smtp.socketFactory.class", value = "javax.net.ssl.SSLSocketFactory")
        properties.put(key = "mail.smtp.auth", value = "true")
        properties.put(key = "mail.smtp.port", value = "465")
        session = Session.getDefaultInstance(properties, this)
        return withContext(Dispatchers.IO){
            try{
                transportMail(toAddress)
            } catch (e: Exception){
                Log.e(TAG, "An exception occurred: ", e)
            }
        }
    }
    private fun transportMail(toAddress: String) {
        val mimeMessage = MimeMessage(session)
        val dataHandler = DataHandler(ByteArrayDataSource(body, "text/plain"))
        mimeMessage.setFrom(fromAddress)
        mimeMessage.subject = subject
        mimeMessage.dataHandler = dataHandler
        mimeMessage.setRecipient(Message.RecipientType.TO, InternetAddress(toAddress))
        val transport = session.getTransport(InternetAddress(toAddress))
        transport.connect()// Mandatory if not using Transport.sendMessage static method
        transport.addTransportListener(this)
        transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO))
        Log.e(TAG, "Sending mail to: " + toAddress)
    }
    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(fromAddress, "chelsea997")
    }

    override fun messageDelivered(e: TransportEvent?) {
        Log.e(TAG,"Message delivered.")
        LoginActivity.updateUI(true, "Delivered", this.toAddress, this.code)
    }

    override fun messageNotDelivered(e: TransportEvent?) {
        Log.e(TAG,"Message NOT delivered.")
        LoginActivity.updateUI(false, "Not Delivered", this.toAddress, this.code)
    }

    override fun messagePartiallyDelivered(e: TransportEvent?) {
        Log.e(TAG,"Message PARTIALLY delivered.")
        LoginActivity.updateUI(false, "Partially Delivered", this.toAddress, this.code)
    }

    override fun opened(e: ConnectionEvent?) {
        Log.e(TAG,"Connection OPENED.")
    }

    override fun disconnected(e: ConnectionEvent?) {
        Log.e(TAG,"Connection DISCONNECTED.")
    }

    override fun closed(e: ConnectionEvent?) {
        Log.e(TAG,"Connection CLOSED.")
    }
}