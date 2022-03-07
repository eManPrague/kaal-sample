package cz.eman.kaalsample.presentation.feature.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.print.PrintHelper
import cz.eman.kaal.presentation.fragment.KaalFragment
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordStrength
import cz.eman.kaalsample.presentation.feature.login.states.LoginStates
import cz.eman.kaalsample.presentation.feature.login.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class LoginFragment : KaalFragment() {

    private val viewModel by viewModel<LoginViewModel>()

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
        viewModel.loginStates.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginStates.IdleState -> Timber.v("waiting to user")
                is LoginStates.LoginDone -> enterTheApp()
                is LoginStates.RegistrationDone -> enterTheApp()
                is LoginStates.ErrorResult -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    viewModel.setToIdle()
                }
            }
        })
    }

    private fun initLayout() {
        loginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginPassword.text.let { viewModel.checkPassword(it.toString()) }
                passwordStrengthIcon.drawable.let { viewModel.checkPassword(it.toString()) }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.passwordStrengthState.observe(viewLifecycleOwner, Observer {
            passwordStrength.setText(it.errorTextId)
            passwordStrength.setTextColor(resources.getColor(it.textColorId))
        })

        switchUseCase(viewModel.loginUseCase)

        switchLogin.setOnClickListener {
            switchUseCase(!viewModel.loginUseCase)
        }

        loginButton.setOnClickListener {
            viewModel.processUser(userName = loginUserName.text.toString().trim(),
                    password = loginPassword.text.toString().trim())
        }

        eman.setOnClickListener {
            loginUserName.setText("john")
            loginPassword.setText("travolta")
        }

    }

    private fun switchUseCase(loginUseCase: Boolean) {
        viewModel.loginUseCase = loginUseCase
        if (loginUseCase) {
            switchLogin.setText(R.string.login_screen_register_u)
            loginButton.setText(R.string.login_screen_login)
        } else {
            switchLogin.setText(R.string.login_screen_login_u)
            loginButton.setText(R.string.login_screen_register)
        }
    }

    private fun enterTheApp() {
        findNavController().navigate(R.id.action_splashLogin_to_dashboardActivity)
        viewModel.setToIdle()
    }
}