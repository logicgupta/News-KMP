import SwiftUI
import shared

@main
struct iOSApp: App {
    // KMM - Koin Call
    init() {
        KoinInitializerKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}