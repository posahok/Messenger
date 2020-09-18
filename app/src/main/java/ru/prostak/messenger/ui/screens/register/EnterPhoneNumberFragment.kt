package ru.prostak.messenger.ui.screens.register

import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*
import ru.prostak.messenger.R
import ru.prostak.messenger.database.AUTH
import ru.prostak.messenger.ui.screens.BaseFragment
import ru.prostak.messenger.utilits.*
import java.util.concurrent.TimeUnit

class EnterPhoneNumberFragment : BaseFragment(R.layout.fragment_enter_phone_number) {
    private lateinit var mPhoneNumber: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onStart() {
        super.onStart()
        /** Колбек - объект, методы которого будут вызываться при срабатывании одного из действий,
         *  за которые отвечает каждая функция*/
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            /** Метод, вызываемый, когда:
             * 1) происходит мнговенная верификация по номеру
             * (может происходить в некоторых случаях без необходимости ввода кода
             * 2) происходит автоматическое получение кода приложением без необходимости ввода кода пользователем*/
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                /** Попытка входа с использованием параметра credential - объекта с учётными данными*/
                AUTH.signInWithCredential(credential).addOnCompleteListener {
                    /** При успешном входе меняется активность*/
                    if (it.isSuccessful){
                        showToast("Добро пожаловать!")
                        restartActivity()
                    }
                    /** При ошибке - выводится сообщение*/
                    else {
                        showToast(it.exception?.message.toString())
                    }
                }
            }
            /** Метод, вызываемый при ошибке верификации: выводится сообщение с ошибкой */
            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())

            }
            /** Метод, вызываемый при отправке проверочного кода: в параметрах id и token.
             * Id и номер телефона будут использоваться в дальнейшем
             * для проверки совпадения номера телефона и проверочного кода*/
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }
        }
        register_btn_next.setOnClickListener {
            sendCode()
        }
        showSoftKeyboard(register_input_phone_number)
    }

    private fun sendCode() {
        if (register_input_phone_number.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_enter_phone))
        } else {
            authUser()
        }
    }

    private fun authUser() {
        /** Сохранение введенного пользователем номера телефона в переменную mPhoneNumber */
        mPhoneNumber = register_input_phone_number.text.toString()
        /** Верификация введенного номера, обработка полученного результата с помощью callback'a */
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            APP_ACTIVITY,
            mCallback
        )
    }
}