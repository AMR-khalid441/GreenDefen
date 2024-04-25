package com.example.greendefend.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greendefend.R
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.databinding.FragmentLoginBinding
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.useCase.AccountViewModel
import com.example.greendefend.ui.homing.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModelAccount: AccountViewModel by viewModels()

    @Inject
    lateinit var dataStorePrefrenceImpl: DataStorePrefrenceImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val span = SpannableString(getString(R.string.green_defend))
        val fcBlack = ForegroundColorSpan(Color.BLACK)
        val fcGreen =
            ForegroundColorSpan(resources.getColor(R.color.green_name, requireContext().theme))
        val size = getString(R.string.green_defend).length
        span.setSpan(fcBlack, 0, size / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(fcGreen, size / 2, size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtAppName.text = span

        binding.btnCreate.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
        binding.txtForget.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetFragment())
        }

        binding.btnLogin.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
runBlocking {  dataStorePrefrenceImpl.putPreference(stringPreferencesKey("Test Save"),"welcome") }
            loginAndObserve(
                Login(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            )
        }

    }

    private fun loginAndObserve(login: Login) {
        viewModelAccount.login(login)

        viewModelAccount.connectionError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                viewModelAccount.rest()
            }
        }
        viewModelAccount.serverResponse.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), "Sucessfull", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                viewModelAccount.rest()
            startActivity(Intent(requireActivity(),HomeActivity::class.java))
                requireActivity().finish()


            }
        }


    }


}



