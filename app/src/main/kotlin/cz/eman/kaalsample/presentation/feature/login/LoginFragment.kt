package cz.eman.kaalsample.presentation.feature.login

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.print.PrintHelper
import cz.eman.kaal.presentation.fragment.KaalFragment
import cz.eman.kaalsample.R
import cz.eman.kaalsample.presentation.feature.login.states.LoginStates
import cz.eman.kaalsample.presentation.feature.login.viewModel.LoginViewModel
import cz.eman.kaalsample.presentation.feature.login.viewModel.SecurityViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class LoginFragment : KaalFragment() {

    private val viewModel by viewModel<LoginViewModel>()
    private val securityViewModel by viewModel<SecurityViewModel>()

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

        securityViewModel.passwordStrength.observe(viewLifecycleOwner) {
            if (!viewModel.loginUseCase && it.textId != null)  {
                loginPasswordInputLayout.setErrorTextAppearance(it.styleId ?: R.style.errorAppearance)
                loginPasswordInputLayout.error = getString(it.textId)
            } else loginPasswordInputLayout.error = null

        }
    }

    private fun initLayout() {
        switchUseCase(viewModel.loginUseCase)

        loginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    loginPassword.text.let { securityViewModel.onPasswordChanged(it.toString()) }
            }
        })

        switchLogin.setOnClickListener {
            switchUseCase(!viewModel.loginUseCase)
        }

        loginButton.setOnClickListener {
            if (!viewModel.loginUseCase &&
                (securityViewModel.passwordStrength.value?.isAcceptable == false) ||
                securityViewModel.passwordStrength.value == null
            ){
                Toast.makeText(requireContext(), R.string.login_password_requirements_not_met, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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
            loginPasswordInputLayout.error = null

            switchLogin.setText(R.string.login_screen_register_u)
            loginButton.setText(R.string.login_screen_login)
        } else {
            val lastTextValue = securityViewModel.passwordStrength.value?.textId
            val errorText = if (lastTextValue != null) getString(lastTextValue) else null
            loginPasswordInputLayout.error = errorText

            switchLogin.setText(R.string.login_screen_login_u)
            loginButton.setText(R.string.login_screen_register)
        }
    }

    private fun enterTheApp() {
        findNavController().navigate(R.id.action_splashLogin_to_dashboardActivity)
        viewModel.setToIdle()
    }
}