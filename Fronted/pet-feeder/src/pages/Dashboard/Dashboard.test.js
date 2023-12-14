import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import Dashboard from "./Dashboard";
import { MemoryRouter } from "react-router-dom";

jest.mock("../../api/axios", () => ({
  get: jest.fn(() => Promise.resolve({ data: {} })),
}));

describe("Dashboard Component", () => {
  it("renders without errors", () => {
    <MemoryRouter>
      render(
      <Dashboard />
      );
    </MemoryRouter>;

    // You can add more specific assertions based on your component structure
    expect(screen.getByText(/dashboard/i)).toBeInTheDocument();
  });

  it("opens DispensePop modal when Dispense button is clicked", () => {
    render(<Dashboard />);

    // Ensure that the DispensePop modal is initially closed
    expect(screen.queryByText(/dispense/i)).not.toBeInTheDocument();

    // Click on the Dispense button
    fireEvent.click(screen.getByText(/dispense/i));

    // Ensure that the DispensePop modal is now open
    expect(screen.getByText(/portion size/i)).toBeInTheDocument();
  });

  it("closes DispensePop modal when the close button is clicked", () => {
    render(<Dashboard />);

    // Click on the Dispense button
    fireEvent.click(screen.getByText(/dispense/i));

    // Ensure that the DispensePop modal is open
    expect(screen.getByText(/portion size/i)).toBeInTheDocument();

    // Click on the close button
    fireEvent.click(screen.getByText(/close/i));

    // Ensure that the DispensePop modal is closed
    expect(screen.queryByText(/portion size/i)).not.toBeInTheDocument();
  });
});
