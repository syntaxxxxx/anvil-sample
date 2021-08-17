package id.android.anvil.home.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.android.anvil.BuildConfig
import id.android.anvil.R
import id.android.anvil.home.domain.HomeParams
import id.android.anvil.home.presentation.HomeViewState.Error
import id.android.anvil.home.presentation.HomeViewState.Loading
import id.android.anvil.home.presentation.HomeViewState.Success
import javax.inject.Inject
import id.android.anvil.MainApplication.Companion.component as Injector

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

  @Inject lateinit var factory: ViewModelProvider.Factory

  private val viewModel: HomeView by lazy {
    ViewModelProvider(this, factory)[HomeViewModel::class.java]
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    Injector.inject(this)

    viewModel.states.observe(this, { state ->
      when (state) {
        is Loading -> Log.d("HOME", "LOADING...")
        is Success -> Log.d("HOME", "SUCCESS!")
        is Error -> Log.d("HOME", "ERROR!")
      }
    })

    viewModel.fetchHome(params = HomeParams(apiKey = BuildConfig.API_KEY))

    super.onCreate(savedInstanceState)
  }
}