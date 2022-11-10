package cz.eman.kaalsample.presentation.feature.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.print.PrintHelper
import cz.eman.kaal.presentation.fragment.KaalFragment
import cz.eman.kaalsample.R
import cz.eman.kaalsample.presentation.feature.login.states.LoginStates
import cz.eman.kaalsample.presentation.feature.login.viewModel.LoginViewModel
import cz.eman.kaalsample.presentation.feature.login.viewModel.PswdStrengthViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


/**
 *  @author stefan.toth@eman.cz
 */
class LoginFragment : KaalFragment() {

    private val loginViewModel by viewModel<LoginViewModel>()
    private val pswdStrengthViewModel by viewModel<PswdStrengthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val orientation = activity?.let { getResources().getConfiguration().orientation }

        when (orientation) {
            PrintHelper.ORIENTATION_PORTRAIT -> view.setBackgroundResource(R.drawable.splash_land)
            PrintHelper.ORIENTATION_LANDSCAPE -> view.setBackgroundResource(R.drawable.splash)
        }

        registerEvents()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun registerEvents() {
        loginViewModel.loginStates.observe(viewLifecycleOwner) {
            when (it) {
                is LoginStates.IdleState -> Timber.v("waiting to user")
                is LoginStates.LoginDone -> enterTheApp()
                is LoginStates.RegistrationDone -> enterTheApp()
                is LoginStates.ErrorResult -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    loginViewModel.setToIdle()
                }
            }
        }

        // JK Colors from resources https://stackoverflow.com/questions/4602902/how-to-set-the-text-color-of-textview-in-code
        pswdStrengthViewModel.data.observe(viewLifecycleOwner) {
            if(!loginViewModel.loginUseCase && it != null) {
                loginButton.isEnabled = it.isOk
                pswdStrengthHint.setText(it.message)
                pswdStrengthHint.setTextColor(it.color)
            }
        }
    }

    private fun initLayout() {
        switchUseCase(loginViewModel.loginUseCase)

        switchLogin.setOnClickListener {
            switchUseCase(!loginViewModel.loginUseCase)
        }

        loginButton.setOnClickListener {
            loginViewModel.processUser(userName = loginUserName.text.toString().trim(),
                    password = loginPassword.text.toString().trim())
        }

        eman.setOnClickListener {
            loginUserName.setText("john")
            loginPassword.setText("travolta")
        }

        loginPassword.addTextChangedListener(object : TextWatcher {
            // TODO: Maybe after text changed?
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                pswdStrengthViewModel.onPswdChanged(loginPassword.text.toString())
            }
        })
    }

    private fun switchUseCase(loginUseCase: Boolean) {
        loginViewModel.loginUseCase = loginUseCase
        if (loginUseCase) {
            switchLogin.setText(R.string.login_screen_register_u)
            loginButton.setText(R.string.login_screen_login)
            pswdStrengthHint.visibility = View.INVISIBLE
            loginButton.isEnabled = true
        } else {
            switchLogin.setText(R.string.login_screen_login_u)
            loginButton.setText(R.string.login_screen_register)
            pswdStrengthHint.visibility = View.VISIBLE
            pswdStrengthViewModel.data.value?.let {
                    loginButton.isEnabled = it.isOk
            }
        }
    }

    private fun enterTheApp() {
        findNavController().navigate(R.id.action_splashLogin_to_dashboardActivity)
        loginViewModel.setToIdle()
    }
}