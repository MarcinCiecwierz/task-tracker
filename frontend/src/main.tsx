import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import { Auth0Provider } from "@auth0/auth0-react";
import { Provider } from "@/components/ui/provider";
import { BrowserRouter, Route, Routes } from "react-router";
import AfterLogin from "./AfterLogin.tsx";
import Task from "./Task.tsx";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Provider>
          <Auth0Provider
            domain="dev-grnq2iwxpxi2aien.us.auth0.com"
            clientId="ha5m8pnON23G8x6nYlRTQgMg2OPQ8Rs0"
            authorizationParams={{
              redirect_uri: window.location.origin,
              audience: "https://dev-grnq2iwxpxi2aien.us.auth0.com/api/v2/",
            }}
          >
            <Routes>
              <Route path="/" element={<App />} />
              <Route path="/tracker" element={<AfterLogin />} />
              <Route path="/task" element={<Task />} />
            </Routes>
          </Auth0Provider>
        </Provider>
      </BrowserRouter>
    </QueryClientProvider>
  </StrictMode>
);
